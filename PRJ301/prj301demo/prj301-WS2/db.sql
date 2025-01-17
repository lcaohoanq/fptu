CREATE DATABASE PRJ301WS2

GO

USE PRJ301WS2

GO

CREATE TABLE Workouts (
    WorkoutID INT PRIMARY KEY,
    WorkoutName VARCHAR(255),
    DurationInMinutes INT,
    WorkoutDate DATE
);

CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(30)
    
);

INSERT INTO Workouts (WorkoutID, WorkoutName, DurationInMinutes, WorkoutDate) VALUES
(1, 'Morning Run', 30, '2024-03-05'),
(2, 'Full Body Workout', 45, '2024-03-06'),
(3, 'Yoga Session', 60, '2024-03-07'),
(4, 'Cardio Kickboxing', 40, '2024-03-08');

INSERT users (username, password, name) VALUES
('dung','dung','Huynh Dung');
GO
INSERT users (username, password, name) VALUES
('nam','nam','Nguyen Nam');
GO