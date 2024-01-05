--- member 데이터
INSERT INTO member (email, username, oauth_id, o_auth_provider, is_ban, account_type)
VALUES ('admin@example.com', 'admin', 'admin123', 'NAVER', false, 'ROLE_ADMIN');

INSERT INTO member (email, username, oauth_id, o_auth_provider, is_ban, account_type)
VALUES ('user@naver.com', 'admin', '112233', 'NAVER', true, 'ROLE_USER');


INSERT INTO member (email, username, oauth_id, o_auth_provider, is_ban, account_type)
VALUES ('kevin0928@naver.com', 'admin', '123123123', 'NAVER', false, 'ROLE_ADMIN');

--- Restaurant 데이터
INSERT INTO Post (point, restaurant_name, scrap_count, visitor_review_score, visitor_review_count)
VALUES (ST_GeomFromText('POINT(26.9780 37.5665)', 4326), '한식', 0, 0, 0);

INSERT INTO Post (point, restaurant_name, scrap_count, visitor_review_score, visitor_review_count)
VALUES (ST_GeomFromText('POINT(26.9781 37.5666)', 4326), '양식', 0, 0, 0);

INSERT INTO Post (point, restaurant_name, scrap_count, visitor_review_score, visitor_review_count)
VALUES (ST_GeomFromText('POINT(26.9782 37.5667)', 4326), '중식', 0, 0, 0);

INSERT INTO Post (point, restaurant_name, scrap_count, visitor_review_score, visitor_review_count)
VALUES (ST_GeomFromText('POINT(26.9783 37.5668)', 4326), '일식', 0, 0, 0);

INSERT INTO Post (point, restaurant_name, scrap_count, visitor_review_score, visitor_review_count)
VALUES (ST_GeomFromText('POINT(26.9784 37.5669)', 4326), '그 외', 0, 0, 0);