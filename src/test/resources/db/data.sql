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
       (now(), 'LAND', 'PASSAGE', 'YUTONG'),
       (now(), 'AERIAL', 'PASSAGE', 'BOING 777'),
       (now(), 'MARITIME', 'BURDEN', 'QUEEN OF SEA');

-- -- ----------------------------
-- -- Records of passenger
-- -- ----------------------------
INSERT INTO passenger(name, transport_id)
VALUES ('Kuko', 0),
       ('Pedro', 1),
       ('Juan', 1),
       ('Pito', 2),
       ('Maria', 2),
       ('Sara', 3),
       ('Rita', 3),
       ('Felo', 4),
       ('Lurdes', 4);

insert into (passenger_id, city,)
