package com.lk.RailwayDepartment.Model;

import com.lk.RailwayDepartment.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribersListFilter {
    private Role role;
    private boolean active;
    private String fromDate;
    private String toDate;

    private boolean isDate;

    public SubscribersListFilter(boolean active) {
        this.active=active;
        this.fromDate=null;
        this.toDate=null;
    }
}
