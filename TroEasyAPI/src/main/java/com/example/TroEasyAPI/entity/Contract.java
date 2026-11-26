package com.example.TroEasyAPI.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract {

    public enum ContractStatus {
        draft,       // Hợp đồng nháp, đang soạn thảo hoặc chờ khách ký
        active,      // Đang có hiệu lực (khách đang ở và đóng tiền theo kỳ)
        expired,     // Hợp đồng đã đến ngày hết hạn nhưng chưa tái ký
        terminated   // Đã thanh lý / chấm dứt trước hạn (khách trả phòng sớm)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String contractNumber; // Mã hợp đồng (VD: HD-101-2026)

    // Thuộc phòng nào
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Người đại diện hợp đồng (chọn trong danh sách người thuê thuộc phòng đó)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(nullable = false)
    private LocalDate startDate; // Ngày bắt đầu hợp đồng

    @Column(nullable = false)
    private LocalDate endDate; // Ngày kết thúc hợp đồng

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal rentalPrice; // Giá thuê phòng hàng tháng

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal deposit; // Số tiền cọc

    @Column(nullable = false)
    @Builder.Default
    private Integer billingDay = 1; // Ngày thu tiền hàng tháng (1–31)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ContractStatus status = ContractStatus.draft;

    @Column(length = 500)
    private String note; // Ghi chú hợp đồng

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
