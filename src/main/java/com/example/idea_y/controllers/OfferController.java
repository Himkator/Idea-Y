package com.example.idea_y.controllers;

import com.example.idea_y.models.CompanyOffers;
import com.example.idea_y.models.PeopleOffers;
import com.example.idea_y.models.User;
import com.example.idea_y.sercives.OfferService;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/")
    public String offers(Model model,  Principal principal){
        model.addAttribute("companyOffer", offerService.getCompanyOffers());
        model.addAttribute("peopleOffer", offerService.getPeopleOffers());
        model.addAttribute("user", offerService.getUserByPrincipal(principal));
        return "offers";
    }
    @GetMapping("/companyOffers")
    public String companyOffer(@RequestParam(name="searchWord", required = false) String title, @RequestParam(name="SearchCate", required = false) String cate, Model model, Principal principal){
        model.addAttribute("companyOffer", offerService.getCompanyOffers(title, cate));
        model.addAttribute("user", offerService.getUserByPrincipal(principal));
        return "companyOffers";
    }
    @GetMapping("/peopleOffers")
    public String peopleOffer(@RequestParam(name="searchWord", required = false)String title,@RequestParam(name="SearchCate", required = false) String cate,Model model, Principal principal){
        model.addAttribute("peopleOffer", offerService.getPeopleOffers(title, cate));
        model.addAttribute("user", offerService.getUserByPrincipal(principal));
        return "peopleOffers";
    }
    @GetMapping("/companyOffer/{id}")
    public String companyOfferInfo(@PathVariable Long id, Model model, Principal principal){
        CompanyOffers companyOffers=offerService.getCompanyById(id);
        model.addAttribute("companyOffer", companyOffers);
        model.addAttribute("user", offerService.getUserByPrincipal(principal));
        model.addAttribute("author", companyOffers.getUser());
        model.addAttribute("images", companyOffers.getImageCompanyList());
        return "companyOffers-info";
    }
    @GetMapping("/peopleOffer/{id}")
    public String peopleOfferInfo(@PathVariable Long id, Model model, Principal principal){
        PeopleOffers peopleOffers=offerService.getPeopleById(id);
        model.addAttribute("peopleOffer", peopleOffers);
        model.addAttribute("user", offerService.getUserByPrincipal(principal));
        model.addAttribute("author", peopleOffers.getUser());
        model.addAttribute("image", peopleOffers.getPhoto());
        return "peopleOffers-info";
    }
    @PostMapping("/companyOffer/create")
    public String createCompanyOffer(@RequestParam(value = "file1", required = false) MultipartFile file1, @RequestParam(value = "file2", required = false) MultipartFile file2, CompanyOffers companyOffers,Principal principal) throws IOException{
        offerService.saveCompanyOffer(principal, file1, file2,companyOffers);
        return "redirect:/companyOffers";
    }
    @PostMapping("/peopleOffer/create")
    public String createPeopleOffer(Principal principal, @RequestParam("file1") MultipartFile file1, PeopleOffers peopleOffers) throws IOException {
        offerService.savePeopleOffer(principal, file1,peopleOffers);
        return "redirect:/peopleOffers";
    }
    @PostMapping("/companyOffer/delete/{id}")
    public String deleteCompanyOffer(@PathVariable Long id, Principal principal){
        offerService.deleteCompanyOffer(offerService.getUserByPrincipal(principal), id);
        return "redirect:/profile";
    }
    @GetMapping("/my/company")
    public String userCompany(Principal principal, Model model){
        User user=offerService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("company", user.getCompanyOffers());
        return "my-company";
    }
    @GetMapping("/my/people")
    public String userPeople(Principal principal, Model model){
        User user=offerService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("people", user.getPeopleOffers());
        return "my-people";
    }
}