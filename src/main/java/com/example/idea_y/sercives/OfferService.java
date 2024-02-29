package com.example.idea_y.sercives;

import com.example.idea_y.models.*;
import com.example.idea_y.repositories.CompanyOfferRepository;
import com.example.idea_y.repositories.PeopleOffersRepository;
import com.example.idea_y.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfferService {
    private final PeopleOffersRepository peopleOffersRepository;
    private final CompanyOfferRepository companyOfferRepository;
    private final UserRepository userRepository;

    public List<PeopleOffers> getPeopleOffers(String text, String cate){
        if(text==null && cate==null) return peopleOffersRepository.findAll();
        if(text.isEmpty() && cate==null) return peopleOffersRepository.findAll();
        if(text!=null && cate==null)return getByName(peopleOffersRepository.findAll(), text);
        else if((text==null || text.isEmpty()) && cate!=null){
            List<PeopleOffers> peopleOffers1=peopleOffersRepository.findAll();
            return cateSortPeople(peopleOffers1, cate);
        }
        else if(text!=null && cate!=null){
            List<PeopleOffers> titleList=getByName(peopleOffersRepository.findAll(), text);
            return cateSortPeople(titleList, cate);
        }
        return peopleOffersRepository.findAll();
    }
    public List<CompanyOffers> getCompanyOffers(String text, String cate){
        if(text==null && cate==null) return companyOfferRepository.findAll();
        if(text.isEmpty() && cate==null) return companyOfferRepository.findAll();
        else if(text!=null && cate==null) return getByTitle(companyOfferRepository.findAll(), text);
        else if((text==null || text.isEmpty()) && cate!=null) {
            List<CompanyOffers> companyOffers1=companyOfferRepository.findAll();
            return cateSortCompany(companyOffers1, cate);
        }
        else if(text!=null && cate!=null){
            List<CompanyOffers> titleList=getByTitle(companyOfferRepository.findAll(), text);
            return cateSortCompany(titleList, cate);
        }
        return companyOfferRepository.findAll();
    }
    private List<CompanyOffers> cateSortCompany(List<CompanyOffers> companyOffers, String cate){
        List<CompanyOffers> cateList=new ArrayList<>();
        for (int i = 0; i < companyOffers.size(); i++) {
            if(companyOffers.get(i).getCategories().contains(cate) || cate.contains(companyOffers.get(i).getCategories())){
                cateList.add(companyOffers.get(i));
            }
        }
        return cateList;
    }
    private List<PeopleOffers> cateSortPeople(List<PeopleOffers> peopleOffers, String cate){
        List<PeopleOffers> cateList=new ArrayList<>();
        for (int i = 0; i < peopleOffers.size(); i++) {
            if(peopleOffers.get(i).getCategories().contains(cate) || cate.contains(peopleOffers.get(i).getCategories())){
                cateList.add(peopleOffers.get(i));
            }
        }
        return cateList;
    }
    public List<PeopleOffers> getPeopleOffers(){
        return peopleOffersRepository.findAll();
    }
    public List<CompanyOffers> getCompanyOffers(){
        return companyOfferRepository.findAll();
    }
    public void saveCompanyOffer(Principal principal, MultipartFile back_photo,MultipartFile photo,CompanyOffers companyOffers){
        companyOffers.setUser(getUserByPrincipal(principal));
        ImageCompany image1;
        ImageCompany image2;
        if(!back_photo.isEmpty()){
            if(back_photo.getSize()!=0){
                try{
                    image1=toImageCompanyEntity(back_photo);
                    companyOffers.addImageCompany(image1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(photo.getSize()!=0){
            try{
                image2=toImageCompanyEntity(photo);
                image2.setPreviewImage(true);
                companyOffers.addImageCompany(image2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Saving new company offer.Title {}", companyOffers.getTitle());
        CompanyOffers OfferFromDb=companyOfferRepository.save(companyOffers);
        getUserByPrincipal(principal).addComapnyToUser(companyOffers);
        if(OfferFromDb.getImageCompanyList().size()==2) {
            OfferFromDb.setPreviewImageId(OfferFromDb.getImageCompanyList().get(1).getId());
        }
        else{OfferFromDb.setPreviewImageId(OfferFromDb.getImageCompanyList().get(0).getId());}
        companyOfferRepository.save(companyOffers);
    }
    private ImageCompany toImageCompanyEntity(MultipartFile file1) throws IOException {
        ImageCompany image=new ImageCompany();
        image.setName(file1.getName());
        image.setSize(file1.getSize());
        image.setBytes(file1.getBytes());
        return image;
    }

    public User getUserByPrincipal(Principal principal) {
        if(principal==null)return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void savePeopleOffer(Principal principal, MultipartFile photo,PeopleOffers peopleOffers) throws IOException {
        peopleOffers.setUser(getUserByPrincipal(principal));
        if(photo.getSize()!=0){
            peopleOffers.setPhoto(photo.getBytes());
        }
        log.info("Saving new people offer.Name {};", peopleOffers.getName());
        peopleOffersRepository.save(peopleOffers);
    }
    public void deleteCompanyOffer(User user, long id){
        CompanyOffers companyOffers=companyOfferRepository.findById(id).orElse(null);
        if(companyOffers!=null){
            if(companyOffers.getUser().getId().equals(user.getId())){
                companyOfferRepository.delete(companyOffers);
                log.info("Company offer  with id = {} was deleted", id);
            }else {
                log.error("User: {} haven't this company offer with id = {}", user.getEmail(), id);
            }
        }else{
            log.error("Company offer with id = {} is not found", id);
        }
    }
    public void deletePeopleOffer(User user, long id){
        PeopleOffers peopleOffers=peopleOffersRepository.findById(id).orElse(null);
        if(peopleOffers!=null){
            if(peopleOffers.getUser().getId().equals(user.getId())){
                peopleOffersRepository.delete(peopleOffers);
                log.info("People offer  with id = {} was deleted", id);
            }else {
                log.error("User: {} haven't this people offer with id = {}", user.getEmail(), id);
            }
        }else{
            log.error("People offer with id = {} is not found", id);
        }
    }

    public CompanyOffers getCompanyById(long id){
        return companyOfferRepository.findById(id).orElse(null);
    }
    public PeopleOffers getPeopleById(long id){
        return peopleOffersRepository.findById(id).orElse(null);
    }

    private List<CompanyOffers> getByTitle(List<CompanyOffers> all,String name){
        List<CompanyOffers> allByTitle=new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getTitle().contains(name) || name.contains(all.get(i).getTitle())){
                allByTitle.add(all.get(i));
            }
        }
        return allByTitle;
    }
    private List<PeopleOffers> getByName(List<PeopleOffers> all,String name){
        List<PeopleOffers> allByTitle=new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getName().contains(name) || name.contains(all.get(i).getName())){
                allByTitle.add(all.get(i));
            }
        }
        return allByTitle;
    }
}
