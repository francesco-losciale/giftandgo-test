CREATE TABLE request_sent
(
    ID               UUID PRIMARY KEY,
    URI              VARCHAR(255),
    CREATED_AT       TIMESTAMP,
    HTTP_STATUS_CODE INT,
    IP_ADDRESS       VARCHAR(45),
    COUNTRY_CODE     VARCHAR(2),
    IP_PROVIDER      VARCHAR(255),
    RESPONSE_TIME    BIGINT
);
