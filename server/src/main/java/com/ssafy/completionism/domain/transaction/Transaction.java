package com.ssafy.completionism.domain.transaction;

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
    // TODO: 2023-09-03 이거 이넘? 
    @Column(nullable = false)
    private String type;
    private int cost;
    private boolean plus;
    @Column(nullable = false)
    @Lob
    private String place;
    @Column(nullable = false)
    @Lob
    private String diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

    @Builder
    private Transaction(Long id, LocalDateTime time, String type, int cost, boolean plus, String place, String diary, History history) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.cost = cost;
        this.plus = plus;
        this.place = place;
        this.diary = diary;
        this.history = history;
    }
}
