-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO event(date, priority, description)
VALUES (now(), 'MINIMUN', 'descripcion de prueba1'),
       (now(), 'MEDIUM', 'descripcion de prueba2'),
       (now(), 'MAXIMUN', 'descripcion de prueba3');

-- ----------------------------
-- Records of transport
-- ----------------------------
INSERT INTO transport(registration_date, type_vehicle, destination, brand)
VALUES (now(), 'LAND', 'WALK', 'MERCEDES'),
       (now(), 'LAND', 'PASSAGE', 'YUTONG'),
       (now(), 'LAND', 'PASSAGE', 'GIRON'),
       (now(), 'AERIAL', 'PASSAGE', 'BOING 777'),
       (now(), 'MARITIME', 'BURDEN', 'QUEEN OF SEA');

-- -- ----------------------------
-- -- Records of passenger
-- -- ----------------------------
INSERT INTO passenger(name, transport_id)
VALUES ('Kuko', 1),
       ('Pedro', 2),
       ('Juan', 2),
       ('Pito', 3),
       ('Maria', 3),
       ('Sara', 4),
       ('Rita', 4),
       ('Felo', 5),
       ('Lurdes', 5);

insert into address(passenger_id, city, street)
VALUES (1, 'Cienfuegos', 'Prado'),
       (2, 'Cienfuegos', 'San Carlos'),
       (3, 'Cienfuegos', 'Santa Cruz'),
       (4, 'Cienfuegos', 'Prado'),
       (5, 'Bayamo', 'Manuel de Socorro'),
       (6, 'La Habana', 'Malecon'),
       (7, 'Matanzas', 'Los Puentes'),
       (8, 'Villa Clara', 'Vidal'),
       (9, 'Matanzas', 'La Playa');

insert into driver(name, passport)
VALUES ('Pepe', '84021014200'),
       ('Juan', '79120215202'),
       ('Siro', '65062215200');

insert into transport_driver(transport_id, driver_id)
VALUES (3,1),
       (2,2),
       (1,3);