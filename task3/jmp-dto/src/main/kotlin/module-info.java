open module jmp.dto {
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires jakarta.persistence;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.starter.data.jpa;
    requires org.hibernate.orm.core;
    requires jakarta.validation;
    requires spring.hateoas;


    exports com.epam.sp.dto to jmp.service.rest, jmp.service.api, jmp.cloud.service.impl;
    exports com.epam.sp.dto.annotations;
}
