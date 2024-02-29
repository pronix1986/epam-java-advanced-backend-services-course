open module jmp.runner {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.core;
    requires spring.beans;
    requires kotlin.stdlib;
    requires kotlin.reflect;

    requires jmp.service.rest;
    requires spring.context;
    requires springfox.spring.web;
    requires springfox.core;
}