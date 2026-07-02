/// # Catalog
/// > Keep the swag inventory: add, list, and remove items, surviving across runs.
///
/// ## Boundary
/// - `add-item` — add or update a swag item by name
/// - `list-items` — return every stored item
/// - `remove-item` — delete a swag item by name
/// - `search-items` — find items whose name or category matches a query
///
/// ## Requirements
/// ### R1: Add an item
/// - R1.1 — When an item with a name, category, size, and non-negative quantity is added, the BC shall store it in the catalog.
/// - R1.2 — When an item is added whose name already exists, the BC shall overwrite the existing item's fields. _(why: name is the sole identity, so re-adding is an upsert not a conflict)_
/// - R1.3 — If an item is added with a missing name, category, size, or quantity, then the BC shall reject the request.
/// - R1.4 — If an item is added with a negative quantity, then the BC shall reject the request. _(why: zero means out-of-stock, negative is never a real count)_
///
/// ### R2: List items
/// - R2.1 — When a listing is requested, the BC shall return every stored item.
/// - R2.2 — While the catalog is empty, when a listing is requested, the BC shall return an empty result.
///
/// ### R3: Remove an item
/// - R3.1 — When an existing item's name is removed, the BC shall delete it from the catalog.
/// - R3.2 — If a removal names an item absent from the catalog, then the BC shall reject the request.
///
/// ### R4: Persistence
/// - R4.1 — When items have been added and the catalog is reopened in a later run, the BC shall retain the previously stored items.
///
/// ### R5: Search items
/// - R5.1 — When a query is submitted, the BC shall return every item whose name or category contains the query, ignoring case. _(why: issue #1 — search by name and category)_
/// - R5.2 — While no item's name or category contains the query, the BC shall return an empty result.
/// - R5.3 — If the query is blank, then the BC shall reject the request.
///
/// ## Entities
/// - SwagItem, Catalog
///
/// ## Out of scope
/// - orders, fulfilment, and shipping
/// - pricing and payment
package airhacks.catalog;
