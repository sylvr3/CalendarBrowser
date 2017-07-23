DROP TABLE event;

CREATE TABLE event (
  name TEXT,
  location TEXT,
  startDate DATETIME DEFAULT CURRENT_DATE,
  startTime DATETIME DEFAULT CURRENT_TIME,
  endDate DATETIME DEFAULT CURRENT_DATE,
  endTime DATETIME DEFAULT CURRENT_TIME,
  allDay INTEGER,
  description TEXT
  );


INSERT INTO event VALUES
   ('Career Fair','ASU Polytechnic','2015-03-07', '12:00', '2015-03-07', '4:00', 0, "Job fair for Poly students");
INSERT INTO event VALUES
   ('Snowbowl Ski Trip', 'Flagstaff', '2015-03-14', '', '2015-03-14', '', 1, '');
