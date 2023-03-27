package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.TrainClass;
import com.lk.RailwayDepartment.Repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService{

    @Autowired
    private ClassRepository classRepository;
    @Override
    public List<TrainClass> findAllByStatus() {
        return classRepository.findAllByActive();
    }

    @Override
    public TrainClass findById(long id) {
        return classRepository.findById(id).isPresent()?classRepository.findById(id).get():null;
    }
}
