package com.ssafy.completionism.domain.transaction;

import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Transaction extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private LocalDateTime time;

    private int cost;

    private boolean plus;

    private Category category;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    @Lob
    private String diary;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

    @Builder
    private Transaction(Long id, LocalDateTime time, int cost, boolean plus, Category category, String place, String diary, History history) {
        this.id = id;
        this.time = time;
        this.cost = cost;
        this.plus = plus;
        this.category = category;
        this.place = place;
        this.diary = diary;
        this.history = history;
    }

    public void regist(History todayHistory) {
        this.history = todayHistory;
        this.history.addTransaction(this);
    }
}
