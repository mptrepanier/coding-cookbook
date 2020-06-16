  
  SELECT 
  start_station_name,
  started_at,
    ((DATE_PART('day', ended_at - started_at) * 24 + 
                DATE_PART('hour', ended_at - started_at)) * 60 +
                DATE_PART('minute', ended_at - started_at)) * 60 +
                DATE_PART('second', ended_at - started_at) AS duration_seconds,
  AVG(((DATE_PART('day', ended_at - started_at) * 24 + 
                DATE_PART('hour', ended_at - started_at)) * 60 +
                DATE_PART('minute', ended_at - started_at)) * 60 +
                DATE_PART('second', ended_at - started_at)) 
OVER
(
PARTITION BY start_station_name
ORDER BY started_at ASC
ROWS 1 PRECEDING
) AS running_average
FROM
bikes
WHERE start_station_name IS NOT NULL AND ended_at IS NOT NULL AND started_at IS NOT NULL
LIMIT 15;