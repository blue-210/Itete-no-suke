package app.itetenosuke.controller.medicine;

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

import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.medicine.service.MedicineService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class MedicineController {
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private MessageSource message;

	@Autowired
	ObjectMapper mapper;

	@GetMapping("/medicine")
	public String showMedicine(@AuthenticationPrincipal UserDetailsImpl userDetails, 
			Model model) {
		
		List<Medicine> medicineList = medicineService.getMedicineList(userDetails.getUserId());
		
		model.addAttribute("medicineList", medicineList);
		model.addAttribute("contents", "medicine/medicine :: medicine_contents");
		return "home/homeLayout";
	}
	
	@GetMapping("/medicine/delete/{medicineId}")
	public String deleteMedicine(@ModelAttribute Medicine medicine,
			@PathVariable("medicineId") Long medicineId,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			Model model) {
		
		medicine.setUserId(userDetails.getUserId());
		boolean result = medicineService.deleteMedicine(medicine);
		
		if(result) {
			model.addAttribute("resultMessage", message.getMessage("medicine.delete.completed", null, Locale.JAPANESE));
		} else {
			model.addAttribute("resultMessage", message.getMessage("medicine.delete.failed", null, Locale.JAPANESE));
		}
		
		return "redirect:/medicine";
	}
	
	@PostMapping(path = "/medicine/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addMedicine(@RequestBody Medicine medicine,
			@AuthenticationPrincipal UserDetailsImpl userDetails, 
			Model model) throws JsonProcessingException {
		
		medicine.setUserId(userDetails.getUserId());
		
		boolean result = medicineService.editMedicine(medicine);
		
		String resultMessage = null;
		if (result) {
			resultMessage = message.getMessage("medicine.edit.completed", null, Locale.JAPANESE);
		} else {
			resultMessage = message.getMessage("medicine.edit.failed", null, Locale.JAPANESE);
		}
		
		Map<String,String> messageMap = new HashMap<>();
		messageMap.put("resultMessage", resultMessage);
		return mapper.writeValueAsString(messageMap);
	}
	
}
