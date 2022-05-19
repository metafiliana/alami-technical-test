package dev.metafiliana.alamitechnicaltest.member.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nik")
    private String nik;
    @Column(name = "name")
    private String name;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "address")
    private String address;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
