Class data.Trade Extends %Persistent
{

Property stockName As %String;

Property purchaseDate As %Date;

Property purchasePrice As %Numeric;

Property shares As %Integer;

Property trader As data.Person;

Storage Default
{
<Data name="TradeDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>stockName</Value>
</Value>
<Value name="3">
<Value>purchaseDate</Value>
</Value>
<Value name="4">
<Value>purchasePrice</Value>
</Value>
<Value name="5">
<Value>shares</Value>
</Value>
<Value name="6">
<Value>trader</Value>
</Value>
</Data>
<DataLocation>^data.TradeD</DataLocation>
<DefaultData>TradeDefaultData</DefaultData>
<IdLocation>^data.TradeD</IdLocation>
<IndexLocation>^data.TradeI</IndexLocation>
<StreamLocation>^data.TradeS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
