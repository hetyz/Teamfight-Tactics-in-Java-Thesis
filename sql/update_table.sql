update data set kills = kills + ? where id = 1;
update data set death = death + ? where id = 1;
update data set wins = wins + 1 where id = 1;
update data set loses = loses + 1 where id = 1;
update data set golds = golds + ? where id = 1;
update data set spent_gold = spent_gold + ? where id = 1;
update data set kills = 0, death = 0, wins = 0, loses = 0, golds = 0, spent_gold = 0 where id = 1;

COMMIT;