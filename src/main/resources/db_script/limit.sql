DROP TABLE Limitation_Door;
create table Limitation_Door
(
  id               SERIAL PRIMARY KEY,
  doorType_id      INT NOT NULL,
  typeSettings     CHARACTER VARYING(100),
  item_id          INT,
  firstItem        CHARACTER VARYING(100),
  secondItem       CHARACTER VARYING(100),
  startRestriction INT,
  stopRestriction  INT,
  comment          CHARACTER VARYING(100),
  defaultValue     INT NOT NULL,
  pairOfValues     INT NOT NULL
);
