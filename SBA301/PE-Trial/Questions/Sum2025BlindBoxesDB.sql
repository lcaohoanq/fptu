


USE master
GO

CREATE DATABASE Sum2025BlindBoxesDB  
GO

USE Sum2025BlindBoxesDB 
GO

CREATE TABLE SystemAccounts (
    AccountID INT PRIMARY KEY, 
    Username VARCHAR(100) NOT NULL, 
    Email VARCHAR(255) NOT NULL, 
	Password VARCHAR(255) NOT NULL, 
    Role int, 
    IsActive bit DEFAULT 1
);

INSERT INTO SystemAccounts (AccountID, Username, Email, Password, Role, IsActive) VALUES
(1, 'adminsys', 'admin@blindboxes.vn', '@2', 1, 1),
(2, 'johndoe', 'john@blindboxes.vn', '@2', 4, 1),
(3, 'modmichel', 'michel@blindboxes.vn', '@2', 2, 1),
(4, 'vanvan', 'vanavan@blindboxes.vn', '@2',4,  0),
(5, 'devops', 'dev@blindboxes.vn', '@2', 3, 1);


CREATE TABLE BlindBoxCategories (
    CategoryID INT PRIMARY KEY, 
    CategoryName VARCHAR(255) NOT NULL, 
    Description TEXT, 
    RarityLevel VARCHAR(50), 
    PriceRange VARCHAR(100)
);

INSERT INTO BlindBoxCategories (CategoryID, CategoryName, Description, RarityLevel, PriceRange) VALUES
(1, 'Fantasy', 'Mystical creatures, wizards, and legendary beings.', 'Common to Ultra Rare', '$10 - $60'),
(2, 'Gaming', 'Blind boxes featuring characters from popular video games.', 'Common to Epic', '$25 - $70'),
(3, 'Sci-Fi', 'Space, futuristic themes, and robotic collectibles.', 'Rare to Legendary', '$30 - $80'),
(4, 'Anime', 'Popular anime characters and themed mystery figures.', 'Common to Legendary', '$15 - $100'),
(5, 'Steampunk', 'Victorian-era inspired mechanical and fantasy figures.', 'Rare to Epic', '$100 - $190');


CREATE TABLE BlindBoxes (
    BlindBoxID INT PRIMARY KEY, 
    Name VARCHAR(255) NOT NULL, 
    CategoryID INT, 
    Rarity VARCHAR(50), 
    Price DECIMAL(10,2), 
    ReleaseDate DATE, 
    Stock INT,
    CONSTRAINT fk_blindbox_category FOREIGN KEY (CategoryID) REFERENCES BlindBoxCategories(CategoryID) ON DELETE CASCADE
);



INSERT INTO BlindBoxes (BlindBoxID, Name, CategoryID, Rarity, Price, ReleaseDate, Stock) VALUES
(1, 'Mystic Creatures Series 1', 1, 'Rare', 29.99, '2024-01-15', 150),
(2, 'Cyberpunk Warriors', 2, 'Ultra Rare', 49.99, '2023-11-20', 75),
(3, 'Fantasy Legends', 1, 'Common', 19.99, '2024-02-10', 200),
(4, 'Space Explorers', 3, 'Epic', 59.99, '2023-12-05', 50),
(5, 'Neon Anime Stars', 4, 'Legendary', 99.99, '2024-03-01', 25);

INSERT INTO BlindBoxes (BlindBoxID, Name, CategoryID, Rarity, Price, ReleaseDate, Stock) VALUES
(6, 'Retro Arcade Heroes', 2, 'Common', 24.99, '2024-01-30', 180),
(7, 'Mythical Beasts Collection', 1, 'Ultra Rare', 54.99, '2023-10-10', 60),
(8, 'Superhero Legends', 3, 'Rare', 39.99, '2024-02-20', 120),
(9, 'Steampunk Adventurers', 2, 'Epic', 69.99, '2023-12-15', 40),
(10, 'Kawaii Anime Pets', 4, 'Common', 14.99, '2024-03-05', 250);

