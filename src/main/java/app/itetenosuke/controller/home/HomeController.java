package app.itetenosuke.controller.home;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.service.NoteService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class HomeController {
	
	@Autowired
	NoteService noteService;
	
	@GetMapping("/home")
	public String getHome(Model model
			, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		Long userId = userDetails.getUserId();
		List<NoteForm> noteList = noteService.getNoteList(userId);
		
		model.addAttribute("noteList", noteList);
		model.addAttribute("contents", "home/home :: home_contents");
		
		return "home/homeLayout";
	}
	
//	@GetMapping("/userList")
//	public String getUserList(Model model) {
//		model.addAttribute("contents", "login/userList :: userList_contents");
//		
//		List<User> userList = userService.selectMany();
//		int count = userService.count();
//		
//		model.addAttribute("userList", userList);
//		model.addAttribute("userListCount", count);
//		
//		return "login/homeLayout";
//	}
//	
//	@GetMapping("/userDetail/{id}")
//	public String getUserDetail(@ModelAttribute SignupForm form,
//			Model model,
//			@PathVariable("id") String userId) {
//		System.out.println("userId = " + userId);
//		
//		model.addAttribute("contents", "login/userDetail :: userDetail_contents");
//		
//		readioMarriage = initRadioMarrige();
//		
//		model.addAttribute("radioMarriage", readioMarriage);
//		
//		if( userId != null && userId.length() > 0 ) {
//			User user = userService.selectOne(userId);
//			
//			form.setUserId(user.getUserId());
//			form.setUserName(user.getUserName());
//			form.setBirthday(user.getBirthday());
//			form.setAge(user.getAge());
//			form.setMarriage(user.isMarriage());
//			
//			model.addAttribute("signupForm", form);
//		}
//		
//		return "login/homeLayout";
//	}
//	
//	@GetMapping("/userList/csv")
//	public ResponseEntity<byte[]> getUserListCsv(Model model){
//		userService.userCsvOut();
//		
//		byte[] bytes = null;
//		
//		try {
//			bytes = userService.getFile("sample.csv");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		HttpHeaders header = new HttpHeaders();
//		header.add("Content-Type", "text/csv; charset=UTF-8");
////		header.setContentDispositionFormData("filename", "sample.csv");
//		header.add("Content-Disposition", "attachment; filename=sample.csv");
//		
//		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
//	}
//	
//	@GetMapping("/admin")
//	public String getAdmin(Model model) {
//		model.addAttribute("contents", "login/admin :: admin_contents");
//		return "login/homeLayout";
//	}
//
//	@PostMapping("/logout")
//	public String postLogout() {
//		return "redirect:/login";
//	}
//	
//	@PostMapping(value = "/userDetail", params = "update")
//	public String postUserDetailUpdate(@ModelAttribute SignupForm form,
//			Model model) {
//		System.out.println("userId = " + form.getUserId());
//		
//		User user = new User();
//		user.setUserId(form.getUserId());
//		user.setUserName(form.getUserName());
//		user.setAge(form.getAge());
//		user.setBirthday(form.getBirthday());
//		user.setMarriage(form.isMarriage());
//		user.setPassword(form.getPassword());
//		
//		try {
//			boolean result = userService.updateOne(user);
//			
//			if(result == true) {
//				model.addAttribute("result", "更新成功");
//			} else {
//				model.addAttribute("result", "更新失敗");
//			}
//		} catch (DataAccessException e) {
//			model.addAttribute("result", "更新失敗トランザクションテスト");
//		}
//		
//		return getUserList(model);
//	}
//	
//	@PostMapping(value = "/userDetail", params = "delete")
//	public String postUserDetailDelete(@ModelAttribute SignupForm form,
//			Model model) {
//		System.out.println("userId = " + form.getUserId());
//		
//		boolean result = userService.deleteOne(form.getUserId());
//		
//		if(result == true) {
//			model.addAttribute("result", "削除成功");
//		} else {
//			model.addAttribute("result", "削除失敗");
//		}
//		
//		return getUserList(model);
//	}
}
