-- name: list-visits
-- Get all visits in the system
SELECT * FROM visits;

-- name: create-visit<!
-- Create visit
INSERT INTO visits (cafe_name, date_visited, beverage, beverage_rating)
VALUES (:cafe_name, :date_visited, :beverage, :beverage_rating)

-- name: update-visit!
-- Update a visit
UPDATE visits SET cafe_name = :cafe_name, date_visited = :date_visited, beverage = :beverage, beverage_rating = :beverage_rating WHERE id = :id

-- name: retrieve-visit
-- Get info about a particular visit
SELECT * FROM visits WHERE id = :id

-- name: get-max-id
-- Get the max ID created for this table
SELECT id FROM visits ORDER BY id DESC LIMIT 1
