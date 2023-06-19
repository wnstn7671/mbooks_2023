package com.ll.mbooks.app.withdraw.entity;

import com.ll.mbooks.app.base.entity.BaseEntity;
import com.ll.mbooks.app.cash.entity.CashLog;
import com.ll.mbooks.app.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class WithdrawApply extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member applicant;
    private String bankName;
    private String bankAccountNo;
    private int price;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private CashLog withdrawCashLog; // 출금에 관련된 내역
    private LocalDateTime withdrawDate;
    private LocalDateTime cancelDate;
    private String msg;

    public WithdrawApply(long id) {
        super(id);
    }

    public boolean isApplyDoneAvailable() {
        if (withdrawDate != null || withdrawCashLog != null || cancelDate != null) {
            return false;
        }

        return true;
    }

    public void setApplyDone(Long cashLogId, String msg) {
        withdrawDate = LocalDateTime.now();
        this.withdrawCashLog = new CashLog(cashLogId);
        this.msg = msg;
    }

    public void setCancelDone(String msg) {
        cancelDate = LocalDateTime.now();
        this.msg = msg;
    }

    public boolean isCancelAvailable() {
        return isApplyDoneAvailable();
    }

    public boolean isApplyDone() {
        return withdrawDate != null;
    }

    public boolean isCancelDone() {
        return cancelDate != null;
    }
}