import com.epam.sp.services.SubscriptionService;
import com.epam.sp.services.UserService;
import com.epam.sp.services.impl.SubscriptionServiceImpl;
import com.epam.sp.services.impl.UserServiceImpl;

open module jmp.cloud.service.impl {
    requires kotlin.stdlib;
    requires jmp.service.api;
    requires jmp.dto;
    requires spring.context;
    requires spring.data.jpa;
    requires modelmapper;
    requires spring.beans;
    requires spring.core;

    provides UserService with UserServiceImpl;
    provides SubscriptionService with SubscriptionServiceImpl;

    exports com.epam.sp.configuration to jmp.runner;
}