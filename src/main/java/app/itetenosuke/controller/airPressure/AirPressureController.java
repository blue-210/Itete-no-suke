package app.itetenosuke.controller.airPressure;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AirPressureController {
	@GetMapping("/airpressure")
	public String showAirPressure(Model model) {
		model.addAttribute("contents", "airPressure/airPressure :: airpressure_contents");
		return "home/homeLayout";
	}
}
