Class data.Person Extends %Persistent
{

Property firstname As %String;

Property lastname As %String;

Property Phonenumber As %String;

Storage Default
{
<Data name="PersonDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>firstname</Value>
</Value>
<Value name="3">
<Value>lastname</Value>
</Value>
<Value name="4">
<Value>Phonenumber</Value>
</Value>
</Data>
<DataLocation>^data.PersonD</DataLocation>
<DefaultData>PersonDefaultData</DefaultData>
<IdLocation>^data.PersonD</IdLocation>
<IndexLocation>^data.PersonI</IndexLocation>
<StreamLocation>^data.PersonS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
