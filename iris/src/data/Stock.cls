Class data.Stock Extends %Persistent
{

Property tDate As %Date;

Property open As %Numeric;

Property high As %Numeric;

Property low As %Numeric;

Property volume As %Integer;

Property TransDate As %Date;

Property StockOpen As %Numeric;

Property StockClose As %Numeric;

Property Name As %String;

Storage Default
{
<Data name="StockDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>tDate</Value>
</Value>
<Value name="3">
<Value>open</Value>
</Value>
<Value name="4">
<Value>high</Value>
</Value>
<Value name="5">
<Value>low</Value>
</Value>
<Value name="6">
<Value>volume</Value>
</Value>
<Value name="7">
<Value>TransDate</Value>
</Value>
<Value name="8">
<Value>StockOpen</Value>
</Value>
<Value name="9">
<Value>StockClose</Value>
</Value>
<Value name="10">
<Value>Name</Value>
</Value>
</Data>
<DataLocation>^data.StockD</DataLocation>
<DefaultData>StockDefaultData</DefaultData>
<IdLocation>^data.StockD</IdLocation>
<IndexLocation>^data.StockI</IndexLocation>
<StreamLocation>^data.StockS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
