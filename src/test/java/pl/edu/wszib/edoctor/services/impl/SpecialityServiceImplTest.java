package pl.edu.wszib.edoctor.services.impl;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.wszib.edoctor.model.Speciality;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpecialityServiceImplTest {

    private List<Speciality> prepareMockData(){
        Speciality speciality1 = new Speciality(0, "Kardiolog");
        Speciality speciality2 = new Speciality(1, "Neurolog");
        List<Speciality> specialities = new ArrayList<>();
        specialities.add(speciality1);
        specialities.add(speciality2);
        return specialities;
    }

    @Test
    public void getAll() {
        SpecialityServiceImpl specialityService = mock(SpecialityServiceImpl.class);
        List<Speciality> specialityList = prepareMockData();
        given(specialityService.getAll()).willReturn(specialityList);
        List<Speciality> test = specialityService.getAll();
        assertThat(test, Matchers.hasSize(2));
    }

    @Test
    public void getSpecialityById() {
        SpecialityServiceImpl specialityService = mock(SpecialityServiceImpl.class);
        List<Speciality> specialityList = prepareMockData();
        given(specialityService.getSpecialityById(1)).willReturn(specialityList.get(1));
        Speciality test = specialityService.getSpecialityById(1);
        Assertions.assertEquals(test, specialityList.get(1));
    }

    @Test
    public void save() {
        SpecialityServiceImpl specialityService = mock(SpecialityServiceImpl.class);
        List<Speciality> specialityList = prepareMockData();
        Speciality specialityTest = new Speciality();
        given(specialityService.save(specialityTest)).willReturn(specialityList.add(specialityTest));
        specialityService.save(specialityTest);
        MatcherAssert.assertThat(specialityList, Matchers.hasSize(3));
    }

    @Test
    public void delete() {
        SpecialityServiceImpl specialityService = mock(SpecialityServiceImpl.class);
        List<Speciality> specialityList = prepareMockData();
        Speciality specialityToDelete = specialityList.get(1);
        given(specialityService.delete(specialityToDelete)).willReturn(specialityList.remove(specialityToDelete));
        specialityService.delete(specialityToDelete);
        MatcherAssert.assertThat(specialityList, Matchers.hasSize(1));
    }

    @Test
    public void update(){
        SpecialityServiceImpl specialityService = mock(SpecialityServiceImpl.class);
        List<Speciality> specialityList = prepareMockData();
        Speciality specialityToUpdate = specialityList.get(1);
        specialityToUpdate.setSpecialityName("Stomatolog");
        specialityService.update(specialityToUpdate);
        Assertions.assertEquals(specialityList.get(1).getSpecialityName(), "Stomatolog");
    }
}