-- Update a visit
UPDATE visits SET cafe_name = :cafe_name, date_visited = :date_visited, beverage = :beverage, beverage_rating = :beverage_rating WHERE id = :id
