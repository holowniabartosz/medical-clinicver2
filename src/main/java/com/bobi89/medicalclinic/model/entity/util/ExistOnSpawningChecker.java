//package com.bobi89.medicalclinic.model.entity.util;
//
//import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
//import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
//import com.bobi89.medicalclinic.repository.LocationJpaRepository;
//
////@Component
////@AllArgsConstructor
//public class ExistOnSpawningChecker {
////    private static PatientJpaRepository patientJpaRepository;
//    private static DoctorJpaRepository doctorJpaRepository;
//    private static LocationJpaRepository locationJpaRepository;
////    public static Long doesPatientExist(String uniqueIdentifier){
////        var entity = patientJpaRepository.findByEmail(uniqueIdentifier);
////        if (entity.isEmpty()){
////            throw new EntityNotFoundException("Entity not found");
////        }
////        return entity.get().getId();
////    }
//
//    public static Long doesDoctorExist(String uniqueIdentifier){
//        var entity = doctorJpaRepository.findByEmail(uniqueIdentifier);
//        if (entity.isEmpty()){
//            throw new EntityNotFoundException("Entity not found");
//        }
//        return entity.get().getId();
//    }
//
//    public static Long doesLocationExist(String uniqueIdentifier){
//        var entity = locationJpaRepository.findByName(uniqueIdentifier);
//        if (entity.isEmpty()){
//            throw new EntityNotFoundException("Entity not found");
//        }
//        return entity.get().getId();
//    }
//}
