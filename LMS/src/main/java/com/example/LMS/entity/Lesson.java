package com.example.LMS.entity;

import com.example.LMS.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(
        name = "lessons",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_course_lesson_order",
                        columnNames = {"course_id", "lessonOrder"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long courseId;

    @Column(nullable = false, length = 200, unique = true)
    String title;

    @Column(nullable = false)
    Integer lessonOrder;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Status status = Status.ACTIVE;

    @Transient
    List<Image> videoUrl;

    @Transient
    List<Image> thumbnail;
}
