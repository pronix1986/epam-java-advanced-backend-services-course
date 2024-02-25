open module jmp.service.rest {
    requires kotlin.stdlib;
    requires jmp.dto;
    requires spring.web;
    requires jmp.service.api;
    requires spring.hateoas;
    requires spring.context;
    requires spring.webmvc;
    requires modelmapper;
    requires spring.core;
    requires io.swagger.v3.oas.annotations;

    uses com.epam.sp.services.UserService;
    uses com.epam.sp.services.SubscriptionService;
}