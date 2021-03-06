package com.example.demo.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.tracker.model.Namespace;
import com.example.demo.tracker.model.Status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NamespaceServiceTest {
    private static final String CODE_0 = "Code_0";
    private static final String CODE_1 = "Code_1";
    @Autowired
    private NamespaceService namespaceService;

    @BeforeEach
    void before() {
        namespaceService.delete(CODE_0, true);
        namespaceService.delete(CODE_1, true);
    }

    @AfterEach
    void after() {

    }

    @Test
    void testCreate() {
        Namespace namespace = namespaceService.create(getNamespaceForTesting());
        assertEquals(CODE_0, namespace.getCode(), "Test Create");
    }

    @Test
    void testDelete() {
        Namespace namespace = namespaceService.create(getNamespaceForTesting());
        Integer newNamespaceId = namespace.getId();
        Boolean successFlag = namespaceService.delete(newNamespaceId);
        assertTrue(successFlag, "success flag deleted");
        assertTrue(namespaceService.read(newNamespaceId).isEmpty(), "Created Device does not exist after delete");
    }

    @Test
    void testUpdate() {
        Namespace namespace = namespaceService.create(getNamespaceForTesting());

        namespace.setName("Updated Name");

        Integer newNamespaceId = namespace.getId();

        namespace = namespaceService.update(newNamespaceId, namespace);

        assertEquals("Updated Name", namespace.getName(), "Updated");
    }

    @Test
    void testList() {
        namespaceService.create(getNamespaceForTesting());
        Namespace namespace2 = getNamespaceForTesting();
        namespace2.setCode(CODE_1);
        namespaceService.create(namespace2);
        assertEquals(2, namespaceService.list(1, 2).size(), "Test Listing");
        assertEquals(1, namespaceService.list(1, 1).size(), "Test Listing with restricted page");
    }

    private Namespace getNamespaceForTesting() {
        Namespace namespace = new Namespace();
        namespace.setStatus(Status.ACTIVE);
        namespace.setCode(CODE_0);
        namespace.setName("KPN");
        return namespace;
    }

}
