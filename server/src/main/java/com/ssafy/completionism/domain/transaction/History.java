package com.ssafy.completionism.domain.transaction;

import com.ssafy.completionism.domain.TimeBaseEntity;
import com.ssafy.completionism.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class History extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int income;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int outcome;

    @Column(nullable = false)
    @Lob
    private String diary;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private History(Long id, LocalDateTime date, String type, int income, int outcome, String diary, Member member) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.income = income;
        this.outcome = outcome;
        this.diary = diary;
        this.member = member;
    }
}
