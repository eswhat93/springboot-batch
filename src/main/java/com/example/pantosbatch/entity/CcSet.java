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
@Table(name="cc_set")
public class CcSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cc_set_seq")
    private int ccSetSeq;

    @Column(name="tnnt_seq")
    private int tnntSeq;

    @Column(name="zzz_cus")
    private int zzzCus;

    @Column(name="frs_ntf_ml")
    private int frsNtfMl;

    @Column(name="scn_ntf_ml")
    private int scnNtfMl;
}