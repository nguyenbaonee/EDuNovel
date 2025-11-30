package com.example.LMS.repo;

import com.example.LMS.entity.Image;
import com.example.LMS.enums.Status;
import com.example.LMS.enums.ImageType;
import com.example.LMS.enums.ObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {

    List<Image> findAllByIdInAndObjectIdAndStatus(List<Long> deleteAvatarsId, Long id, Status status);


    List<Image> findByObjectIdAndStatus(Long id, Status status);

    @Query("SELECT i FROM Image i WHERE i.objectId = :objectId AND i.objectType = :objectType AND i.type = :type AND i.status = :status")
    List<Image> findByObjectIdAndObjectTypeAndTypeAndStatus(@Param("objectId") Long objectId, @Param("objectType") ObjectType objectType, @Param("type") ImageType type, @Param("status") Status status);
}
