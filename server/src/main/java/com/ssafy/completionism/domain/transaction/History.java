package com.ssafy.completionism.domain.transaction;

import com.ssafy.completionism.domain.TimeBaseEntity;
import com.ssafy.completionism.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
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
    @ColumnDefault("0")
    private int income;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int outcome;

    @Column(nullable = false)
    @Lob
    private String diary;
    @Enumerated(STRING)
    @Column(length = 20, nullable = true)
    private Feel feel;


    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public History(Long id, int income, int outcome, String diary, Feel feel, List<Transaction> transactions, Member member) {
        this.id = id;
        this.income = income;
        this.outcome = outcome;
        this.diary = diary;
        this.feel = feel;
        this.transactions = transactions;
        this.member = member;
    }

    public void updateHistory(int cost, boolean plus) {
        if (plus) {
            this.income += cost;
        } else {
            this.outcome += cost;
        }
    }

    public void updateDiary(String diary) {
        this.diary = diary;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
