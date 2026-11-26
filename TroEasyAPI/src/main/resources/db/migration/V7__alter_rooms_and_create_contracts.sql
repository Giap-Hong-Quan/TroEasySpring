-- 1. Cập nhật bảng rooms: thêm area_id và assets
ALTER TABLE rooms ADD COLUMN area_id BIGINT;

-- Tự động điền area_id từ block cho các phòng cũ (nếu có)
UPDATE rooms r SET area_id = b.area_id FROM blocks b WHERE r.block_id = b.id;

ALTER TABLE rooms ALTER COLUMN area_id SET NOT NULL;
ALTER TABLE rooms ADD CONSTRAINT fk_rooms_area FOREIGN KEY (area_id) REFERENCES areas(id) ON DELETE CASCADE;

ALTER TABLE rooms ADD COLUMN assets JSONB DEFAULT '[]';

-- 2. Tạo bảng người thuê (tenants)
CREATE TABLE tenants (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    cccd VARCHAR(20),
    gender VARCHAR(10),
    address VARCHAR(255),
    room_id BIGINT NOT NULL,
    is_representative BOOLEAN NOT NULL DEFAULT FALSE,
    start_date DATE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_tenants_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

-- 3. Tạo bảng hợp đồng (contracts)
CREATE TABLE contracts (
    id BIGSERIAL PRIMARY KEY,
    contract_number VARCHAR(50) NOT NULL UNIQUE,
    room_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    rental_price NUMERIC(12, 2) NOT NULL,
    deposit NUMERIC(12, 2) NOT NULL,
    billing_day INT NOT NULL DEFAULT 1 CHECK (billing_day BETWEEN 1 AND 31),
    status VARCHAR(20) NOT NULL DEFAULT 'draft',
    note VARCHAR(500),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_contracts_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    CONSTRAINT fk_contracts_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE RESTRICT
);
