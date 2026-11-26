package com.example.TroEasyAPI.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tenants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName; // Họ và tên người thuê

    @Column(nullable = false, length = 20)
    private String phone; // Số điện thoại

    @Column(length = 20)
    private String cccd; // Số CCCD / CMND

    @Column(length = 10)
    private String gender; // Giới tính (Nam / Nữ)

    @Column(length = 255)
    private String address; // Quê quán / Địa chỉ thường trú

    @Column(nullable = false)
    private LocalDate startDate; // Ngày vào ở

    // Đang ở phòng nào
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Có phải người đại diện đứng tên hợp đồng không (true: đại diện, false: ở cùng)
    @Column(nullable = false)
    @Builder.Default
    private Boolean isRepresentative = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
