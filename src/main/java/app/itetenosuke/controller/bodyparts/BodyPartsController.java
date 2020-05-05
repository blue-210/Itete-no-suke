package app.itetenosuke.controller.bodyparts;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.bodyParts.service.BodyPartsService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class BodyPartsController {
	@Autowired
	private BodyPartsService service;
	
	@Autowired
	private MessageSource message;
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping("/bodyparts")
	public String showBodyParts(@ModelAttribute BodyParts bodyParts,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			Model model) {
		
		List<BodyParts> bodyPartsList = service.getBodyPartsList(userDetails.getUserId());
		
		model.addAttribute("bodyPartsList", bodyPartsList);
		model.addAttribute("contents", "bodyparts/bodyparts :: bodyparts_contents");
		
		return "home/homeLayout";
	}
	
	@GetMapping("/bodyparts/delete/{bodyPartsId}")
	public String deleteBodyParts(@ModelAttribute BodyParts bodyParts,
			@PathVariable("bodyPartsId") Long bodyPartsId,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			Model model) {
		
		bodyParts.setUserId(userDetails.getUserId());
		bodyParts.setBodyPartsId(bodyPartsId);
		boolean result = service.deleteBodyParts(bodyParts);
		
		if(result) {
			model.addAttribute("resultMessage", message.getMessage("bodyparts.delete.completed", null, Locale.JAPANESE));
		} else {
			model.addAttribute("resultMessage", message.getMessage("bodyparts.delete.failed", null, Locale.JAPANESE));
		}
		
		return "redirect:/bodyparts";
	}
	
	@PostMapping(path = "/bodyparts/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editBodyParts(@RequestBody BodyParts bodyParts,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			Model model) throws JsonProcessingException {
		
		bodyParts.setUserId(userDetails.getUserId());
		boolean result = service.editBodyParts(bodyParts);

		String resultMessage = null;
		if(result) {
			resultMessage = message.getMessage("bodyparts.edit.completed", null, Locale.JAPANESE);
		} else {
			resultMessage = message.getMessage("bodyparts.edit.failed", null, Locale.JAPANESE);
		}
		
		Map<String,String> messageMap = new HashMap<>();
		messageMap.put("resultMessage", resultMessage);
		return mapper.writeValueAsString(messageMap);
	}
	
	@PostMapping("/bodyparts/add")
	public String addBodyParts(@ModelAttribute BodyParts bodyParts,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			Model model) {
		
		bodyParts.setUserId(userDetails.getUserId());
		boolean result = service.addBodyParts(bodyParts);
		
		if(result) {
			model.addAttribute("resultMessage", message.getMessage("bodyparts.create.completed", null, Locale.JAPANESE));
		} else {
			model.addAttribute("resultMessage", message.getMessage("bodyparts.create.failed", null, Locale.JAPANESE));
		}
		return "redirect:/bodyparts";
	}
}
