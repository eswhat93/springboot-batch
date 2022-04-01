package com.example.pantosbatch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="cc_user")
public class CcUser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_seq")
    private int userSeq;

    @Column(name="login_dt")
    private LocalDateTime loginDt;

    @Column(name="lock_yn")
    private String lockYn;

    @Column(name="enc_email")
    private String encEmail;

    public CcUser changeLockYnN(){
        this.lockYn = "Y";
        return this;
    }
//    public CcUser(String loginDt, String lockYn, String encEmail) {
//        this.lockYn = lockYn;
//        this.encEmail = encEmail;
//        this.loginDt = LocalDateTime.parse(loginDt, FORMATTER);
//    }
//
//    public CcUser(Long userSeq, String loginDt, String lockYn, String encEmail) {
//        this.userSeq = userSeq;
//        this.lockYn = lockYn;
//        this.encEmail = encEmail;
//        this.loginDt = LocalDateTime.parse(loginDt, FORMATTER);
//    }
}