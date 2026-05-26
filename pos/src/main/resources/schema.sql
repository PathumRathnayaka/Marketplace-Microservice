ALTER TABLE backup_suppliers
	ADD COLUMN IF NOT EXISTS supplierSource varchar(50),
	ADD COLUMN IF NOT EXISTS marketplaceSupplierId varchar(255),
	ADD COLUMN IF NOT EXISTS marketplaceConnected boolean;

UPDATE backup_suppliers
SET supplierSource = COALESCE(supplierSource, 'LOCAL'),
	marketplaceConnected = COALESCE(marketplaceConnected, false);

ALTER TABLE backup_products
	ADD COLUMN IF NOT EXISTS minimumQuantity numeric;

CREATE TABLE IF NOT EXISTS low_stock_requests (
	id varchar(255) PRIMARY KEY,
	tenant_id varchar(255) NOT NULL,
	product_id varchar(255) NOT NULL,
	product_name varchar(255),
	category varchar(255),
	current_quantity numeric,
	minimum_quantity numeric,
	required_quantity numeric,
	status varchar(50) NOT NULL,
	created_at timestamp,
	updated_at timestamp,
	deleted boolean
);

CREATE INDEX IF NOT EXISTS idx_low_stock_request_tenant
	ON low_stock_requests (tenant_id);

CREATE INDEX IF NOT EXISTS idx_low_stock_request_open_product
	ON low_stock_requests (tenant_id, product_id, status)
	WHERE deleted = false;

CREATE TABLE IF NOT EXISTS supplier_offers (
	id varchar(255) PRIMARY KEY,
	tenant_id varchar(255) NOT NULL,
	request_id varchar(255) NOT NULL,
	supplier_id varchar(255),
	supplier_name varchar(255),
	offered_price numeric,
	delivery_days integer,
	notes text,
	status varchar(50) NOT NULL,
	created_at timestamp,
	updated_at timestamp,
	deleted boolean
);

CREATE INDEX IF NOT EXISTS idx_supplier_offer_tenant
	ON supplier_offers (tenant_id);

CREATE INDEX IF NOT EXISTS idx_supplier_offer_request
	ON supplier_offers (tenant_id, request_id);

CREATE TABLE IF NOT EXISTS supplier_categories (
	id varchar(255) PRIMARY KEY,
	tenant_id varchar(255) NOT NULL,
	supplier_id varchar(255) NOT NULL,
	category_name varchar(255) NOT NULL,
	created_at timestamp,
	updated_at timestamp,
	deleted boolean
);

CREATE INDEX IF NOT EXISTS idx_supplier_category_tenant
	ON supplier_categories (tenant_id);

CREATE INDEX IF NOT EXISTS idx_supplier_category_supplier
	ON supplier_categories (tenant_id, supplier_id);
