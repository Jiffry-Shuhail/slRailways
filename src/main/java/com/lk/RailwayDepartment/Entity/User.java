package com.lk.RailwayDepartment.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please select the Title")
    @Column
    private String title;

    @NotNull(message = "Please enter the First Name")
    @Column
    private String fname;

    @NotNull(message = "Please enter the Last Name")
    @Column
    private String lname;

    @Column
    private String nic;

    @Column
    private String passport;

    @Column
    private boolean active;

    @NotNull(message = "Please enter Contact Number")
    @Column
    private String contact;

    @NotNull(message = "Please enter the Email address")
    @Column(nullable=false, unique=true)
    private String email;

    @NotNull(message = "Please enter Password")
    @Column
    private String password;

    @Column
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL) @JoinColumn(name = "role")
    private Role role;

    @OneToMany(mappedBy="user")
    private List<Booking> bookingList;
}
