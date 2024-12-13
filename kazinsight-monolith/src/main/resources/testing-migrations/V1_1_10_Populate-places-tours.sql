truncate tours;
truncate places;

insert into tours (price, created_at, end_date, start_date, updated_at, created_by, description, name, updated_by, location)
values
    (15000, current_timestamp, '2024-12-20 18:00:00', '2024-12-20 09:00:00', current_timestamp, 'admin@mail.com',
     'Explore the vibrant cultural and economic heart of Kazakhstan with an immersive Almaty City Tour. Nestled at the foot of the majestic Trans-Ili Alatau mountains, Almaty offers a perfect blend of natural beauty, history, and modern charm.',
     'Almaty City Tour', 'admin@mail.com', 'Almaty, Kazakhstan'),

    (10000, current_timestamp, '2024-12-21 18:00:00', '2024-12-21 09:00:00', current_timestamp, 'admin@mail.com',
     'Visit the stunning Kok-Tobe Hill, enjoy panoramic views of Almaty, and experience a thrilling cable car ride. A must-see destination for breathtaking landscapes.',
     'Kok-Tobe Hill Experience', 'admin@mail.com', 'Kok-Tobe, Almaty'),

    (20000, current_timestamp, '2024-12-22 18:00:00', '2024-12-22 09:00:00', current_timestamp, 'admin@mail.com',
     'Discover Medeu Gorge, home to the world-famous ice-skating rink surrounded by majestic alpine scenery. A perfect blend of nature and culture awaits!',
     'Medeu Gorge Adventure', 'admin@mail.com', 'Medeu Gorge, Almaty'),

    (5000, current_timestamp, '2024-12-23 18:00:00', '2024-12-23 09:00:00', current_timestamp, 'admin@mail.com',
     'Immerse yourself in the bustling streets of Arbat, Almaty’s lively pedestrian zone filled with art, shops, and cafes.',
     'Arbat Pedestrian Walk', 'admin@mail.com', 'Arbat, Almaty'),

    (8000, current_timestamp, '2024-12-24 18:00:00', '2024-12-24 09:00:00', current_timestamp, 'admin@mail.com',
     'Shop for fresh produce, spices, and local delicacies at the Green Bazaar, Almaty’s traditional marketplace.',
     'Green Bazaar Tour', 'admin@mail.com', 'Green Bazaar, Almaty');

insert into places (latitude, longitude, created_at, creator_id, updated_at, created_by, description, name, updated_by, location, type)
values
    (43.2220, 76.8512, current_timestamp, 1, current_timestamp, 'admin@mail.com',
     'Kok-Tobe Hill offers stunning panoramic views of Almaty and features a cable car ride and scenic pathways.',
     'Kok-Tobe Hill', 'admin@mail.com', 'Almaty', 'OUTDOOR'),

    (43.2359, 76.9383, current_timestamp, 1, current_timestamp, 'admin@mail.com',
     'Medeu Gorge is home to the world-famous ice-skating rink surrounded by breathtaking alpine landscapes.',
     'Medeu Gorge', 'admin@mail.com', 'Almaty', 'OUTDOOR'),

    (43.2567, 76.9286, current_timestamp, 1, current_timestamp, 'admin@mail.com',
     'The Green Bazaar is a traditional market offering a variety of fresh produce, spices, and local delicacies.',
     'Green Bazaar', 'admin@mail.com', 'Almaty', 'INDOOR'),

    (43.2644, 76.9350, current_timestamp, 1, current_timestamp, 'admin@mail.com',
     'Arbat is a lively pedestrian street featuring local art, shops, and cafes.',
     'Arbat Street', 'admin@mail.com', 'Almaty', 'INDOOR'),

    (43.2389, 76.8319, current_timestamp, 1, current_timestamp, 'admin@mail.com',
     'Zenkov Cathedral is a wooden architectural masterpiece located in Panfilov Park.',
     'Zenkov Cathedral', 'admin@mail.com', 'Almaty', 'INDOOR');