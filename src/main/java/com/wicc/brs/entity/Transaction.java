//package com.wicc.brs.entity;
//
//import com.wicc.brs.enums.ActiveClosed;
//import com.wicc.brs.enums.RentStatus;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(
//        name = "transaction_log",
//        uniqueConstraints = @UniqueConstraint(
//                name="unique_code",
//                columnNames = "code"
//        )
//)
//public class Transaction {
//    @Id
//    @SequenceGenerator(name="transaction_is_gen",sequenceName = "transaction_is_gen",allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "transaction_is_gen")
//    private Integer id;
//
//    @Column(name="code", nullable = false)
//    private String code;
//
//    @Column(name="from_date", nullable = false)
//    private Date fromDate;
//
//    @Column(name="to_date", nullable = false)
//    private Date toDate;
//
//    @Column(name = "rent_status", nullable = false)
//    private RentStatus rentStatus;
//
//    @Column(name = "active_closed", nullable = false)
//    private ActiveClosed activeClosed;
//
//    @ManyToOne
//    @JoinColumn(name = "book_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_transaction_book"))
//    private Book book;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_transaction_member"))
//    private Member member;
//
//}
