package com.sks.sksschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

/*
@Data annotation is provided by Lombok library which generates getter, setter,
equals(), hashCode(), toString() methods & Constructor at compile time.
This makes our code short and clean.

We can use separate annotation as well. Like if we want only getter then @Getter
* */

@Data
@Entity
public class Holiday {

    @Id
    private  String holiday_date;
    private  String reason;

    @Enumerated(EnumType.STRING)
    private  Type type;

    public enum Type{
        NATIONAL,FESTIVAL
    }


    /*
    Commented as we started using Lombok library and we have used @Data above
    which will generate all these.

    public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public String getReason() {
        return reason;
    }

    public Type getType() {
        return type;
    }*/
}
