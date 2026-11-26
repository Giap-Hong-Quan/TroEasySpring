CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price NUMERIC(12, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'vacant',
    block_id BIGINT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_rooms_block FOREIGN KEY (block_id) REFERENCES blocks(id) ON DELETE CASCADE
);
