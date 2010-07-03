select
	a.articleid,
	a.name,
	a.preis,
	pg.name,
	pf.name,
	pc.name 
from
	db2inst1.articleid a,
	db2inst1.productgroupid pg,
	db2inst1.productfamilyid pf,
	db2inst1.productcategoryid pc
where
	a.productgroupid = pg.productgroupid and
	pg.productfamilyid = pf.productfamilyid and
	pf.productcategoryid = pc.productcategoryid