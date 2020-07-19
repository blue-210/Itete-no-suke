package app.itetenosuke.controller.note;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.bodyParts.service.BodyPartsService;
import app.itetenosuke.domain.common.model.Image;
import app.itetenosuke.domain.common.service.ImageService;
import app.itetenosuke.domain.medicine.model.Medicine;
import app.itetenosuke.domain.medicine.service.MedicineService;
import app.itetenosuke.domain.note.model.NoteForm;
import app.itetenosuke.domain.note.model.PainLevel;
import app.itetenosuke.domain.note.service.NoteService;
import app.itetenosuke.domain.user.model.UserDetailsImpl;

@Controller
public class NoteController {
  private static final String COMMON_PAGE_NAME = "home/homeLayout";

  @Autowired
  private NoteService noteService;

  @Autowired
  private MedicineService medicineService;

  @Autowired
  private BodyPartsService bodyPartsService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private MessageSource message;

  private void initBlankNote(Model model, NoteForm note, UserDetailsImpl userDetails) {
    model.addAttribute("painLevelMap", initRadioPainLevel());
    model.addAttribute("note", note);
    model.addAttribute("medicineSelectList", initMedicineListOption(userDetails));
    model.addAttribute("bodyPartsSelectList", initBodyaPartsListOption(userDetails));
    model.addAttribute("isCreate", true);
    model.addAttribute("contents", "note/note :: note_contents");
  }

  private Map<String, Integer> initRadioPainLevel() {
    Map<String, Integer> radio = new LinkedHashMap<>();
    radio.put(PainLevel.NO_PAIN.getName(), PainLevel.NO_PAIN.getCode());
    radio.put(PainLevel.MODERATE.getName(), PainLevel.MODERATE.getCode());
    radio.put(PainLevel.VERY_SEVERE_PAIN.getName(), PainLevel.VERY_SEVERE_PAIN.getCode());
    radio.put(PainLevel.WORST_PAIN_POSSIBLE.getName(), PainLevel.WORST_PAIN_POSSIBLE.getCode());
    return radio;
  }

  private List<BodyParts> initBodyaPartsListOption(UserDetailsImpl userDetails) {
    List<BodyParts> bodyPartsList = bodyPartsService.getBodyPartsList(userDetails.getUserId());
    return bodyPartsList;
  }

  private List<Medicine> initMedicineListOption(UserDetailsImpl userDetails) {
    List<Medicine> medicineList = medicineService.getMedicineList(userDetails.getUserId());
    return medicineList;
  }

  @GetMapping("/note/show/{noteId}")
  public String showNote(@PathVariable("noteId") Long noteId,
      @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
    // 痛み記録を取得する
    NoteForm note = noteService.getNote(userDetails.getUserId(), noteId);
    note.setUserId(userDetails.getUserId());

    model.addAttribute("noteForm", note);
    model.addAttribute("medicineSelectList", initMedicineListOption(userDetails));
    model.addAttribute("bodyPartsSelectList", initBodyaPartsListOption(userDetails));
    model.addAttribute("painLevelMap", initRadioPainLevel());
    model.addAttribute("contents", "note/note :: note_contents");

    return COMMON_PAGE_NAME;
  }

  @GetMapping("/note/list")
  public String showNoteList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

    List<NoteForm> noteList = noteService.getNoteList(userDetails.getUserId());

    model.addAttribute("noteList", noteList);
    model.addAttribute("contents", "note/noteList :: note_list_contents");
    return COMMON_PAGE_NAME;
  }

  @PostMapping(value = "/note", params = "edit")
  public String editNote(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestParam("image-files") List<MultipartFile> files,
      @ModelAttribute @Validated NoteForm noteForm, BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("noteForm", noteForm);
      model.addAttribute("painLevelMap", initRadioPainLevel());
      model.addAttribute("medicineSelectList", initMedicineListOption(userDetails));
      model.addAttribute("bodyPartsSelectList", initBodyaPartsListOption(userDetails));
      model.addAttribute("contents", "note/note :: note_contents");

      return COMMON_PAGE_NAME;
    }

    // 画像を保存する
    List<Image> imageList = new ArrayList<>();
    if (files.stream().allMatch(file -> !file.getOriginalFilename().isEmpty())) {
      imageList = imageService.saveImages(files, userDetails.getUserId());
    }
    noteForm.setImageList(imageList);

    // 痛み記録を編集する
    noteForm.setUserId(userDetails.getUserId());
    Integer noteId = noteService.editNote(noteForm);

    // 編集の成否によってメッセージを変える
    if (noteId > 0) {
      model.addAttribute("resultMessage",
          message.getMessage("note.edit.completed", null, Locale.JAPANESE));
    } else {
      model.addAttribute("resultMessage",
          message.getMessage("note.edit.completed", null, Locale.JAPANESE));
    }
    model.addAttribute("contents", "note/note :: note_contents");

    return "redirect:/note/show/" + noteId;
  }

  @GetMapping("/note/add")
  public String showBlankNote(Model model, @ModelAttribute NoteForm note,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    initBlankNote(model, note, userDetails);
    return COMMON_PAGE_NAME;
  }



  @PostMapping(value = "/note", params = "create")
  public String createNote(RedirectAttributes redirectAttributes,
      @ModelAttribute @Validated NoteForm note, BindingResult bindingResult,
      @RequestParam("image-files") List<MultipartFile> files,
      @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

    if (bindingResult.hasErrors()) {
      initBlankNote(model, note, userDetails);
      return COMMON_PAGE_NAME;
    }

    List<Image> imagesList = new ArrayList<>();
    if (files.stream().allMatch(file -> !file.getOriginalFilename().isEmpty())) {
      imagesList = imageService.saveImages(files, userDetails.getUserId());
    }

    // 痛み記録を登録
    note.setUserId(userDetails.getUserId());
    note.setImageList(imagesList);
    Integer noteId = noteService.createNote(note);

    if (noteId != null) {
      model.addAttribute("resultMessage",
          message.getMessage("note.create.completed", null, Locale.JAPANESE));
    }

    return "redirect:/note/show/" + noteId.toString();
  }
}
