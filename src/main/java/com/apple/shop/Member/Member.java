package com.apple.shop.Member;

import com.apple.shop.sales.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    public String username;
    public String password;
    public String displaynm;

    @OneToMany(mappedBy = "member")
    List<Sales> sales = new ArrayList<>();
}