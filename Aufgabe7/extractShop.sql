select
	s.shopid,
	s.name,
	st.name,
	re.name,
	la.name 
from
	db2inst1.shopid s,
	db2inst1.stadtid st,
	db2inst1.regionid re,
	db2inst1.landid la
where
	s.stadtid = st.stadtid and
	st.regionid = re.regionid and
	re.landid = la.landid
