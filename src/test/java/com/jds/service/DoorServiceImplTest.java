package com.jds.service;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.DoorType;
import com.jds.dao.repository.MainDAO;
import com.jds.model.RestrictionOfSelectionFields;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DoorServiceImplTest {

    @Mock
    MainDAO mockDao;
    @Mock
    OrderService mockOrderService;
    @Mock
    TemplateService mockTemplateService;

    @InjectMocks
    DoorService service = new DoorServiceImpl();

    private int doorId = 1;
    private int orderId = 1;
    private String doorTypeId = "1";

    @Before
    public void method() {

        DoorEntity returnDoor = new DoorEntity();
        returnDoor.setId(doorId);

        DoorType doorType = new DoorType();
        doorType.setId(1);
        returnDoor.setDoorType(doorType);

        DoorOrder returnOrder = new DoorOrder();
        returnOrder.setOrderId(orderId);

        when(mockDao.getDoor(doorId)).thenReturn(returnDoor);

        when(mockOrderService.getOrder(orderId)).thenReturn(returnOrder);

        when(mockTemplateService.newDoor(1, 0)).thenReturn(returnDoor);
        when(mockTemplateService.getTemplate(doorTypeId)).thenReturn(new RestrictionOfSelectionFields());
    }

    @Test
    public void should_call_getDoor_from_DAO() {
        DoorEntity doorEntity = service.getDoor(1, 1, 1);
        verify(mockDao).getDoor(doorId);
    }

    @Test
    public void should_call_getOrder_from_orderService() {
        DoorEntity doorEntity = service.getDoor(1, 1, 1);
        verify(mockOrderService).getOrder(orderId);
    }

    @Test
    public void should_call_getTemplate_from_templateService() {
        DoorEntity doorEntity = service.getDoor(1, 1, 1);
        verify(mockTemplateService).getTemplate(doorTypeId);
    }

    @Test
    public void should_be_set_orderHolder_to_door() {
        int orderId = 1;
        DoorEntity doorEntity = service.getDoor(1, orderId, 1);
        assertEquals(orderId, doorEntity.getOrderHolder());
    }

    @Test
    public void should_call_newDoor_from_templateService() {

        DoorEntity doorEntity = service.getDoor(0, 0, 1);
        verify(mockTemplateService).newDoor(1, 0);
    }

    @Test
    public void should_call_clearNonSerializingFields_from_door() {
        DoorEntity returnDoorEntity = new DoorEntity();
        DoorEntity spyDoorEntity = Mockito.spy(returnDoorEntity);
        when(mockTemplateService.newDoor(1, 0)).thenReturn(spyDoorEntity);

        DoorEntity doorEntity = service.getDoor(0, 0, 1);
        Mockito.verify(spyDoorEntity).clearNonSerializingFields();
    }
}
