package com.softwarecraftsmen.dns;

import static com.softwarecraftsmen.toString.ToString.string;
import com.softwarecraftsmen.Unsigned16BitInteger;
import com.softwarecraftsmen.dns.messaging.serializer.AtomicWriter;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import org.jetbrains.annotations.NotNull;

public class ServiceInformation implements Serializable
{
	private final Unsigned16BitInteger priority;
	private final Unsigned16BitInteger weight;
	private final Unsigned16BitInteger port;
	private final HostName canonicalTargetHostName;

	// target is "." => service not present; target should be CanonicalName; target's A records should be in Additional records...
	public ServiceInformation(final @NotNull Unsigned16BitInteger priority, final @NotNull Unsigned16BitInteger weight, final @NotNull Unsigned16BitInteger port, final @NotNull HostName canonicalTargetHostName)
	{
		this.priority = priority;
		this.weight = weight;
		this.port = port;
		this.canonicalTargetHostName = canonicalTargetHostName;
	}

	@NotNull
	public static ServiceInformation serviceInformation(final @NotNull Unsigned16BitInteger priority, final @NotNull Unsigned16BitInteger weight, final @NotNull Unsigned16BitInteger port, final @NotNull HostName canonicalTargetHostName)
	{
		return new ServiceInformation(priority, weight, port, canonicalTargetHostName);
	}

	@NotNull
	public String toString()
	{
		return string(this, priority, weight, port, canonicalTargetHostName);
	}

	@SuppressWarnings({"RedundantIfStatement"})
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final ServiceInformation that = (ServiceInformation) o;

		if (!canonicalTargetHostName.equals(that.canonicalTargetHostName))
		{
			return false;
		}
		if (!port.equals(that.port))
		{
			return false;
		}
		if (!priority.equals(that.priority))
		{
			return false;
		}
		if (!weight.equals(that.weight))
		{
			return false;
		}
		return true;
	}

	public int hashCode()
	{
		int result;
		result = priority.hashCode();
		result = 31 * result + weight.hashCode();
		result = 31 * result + port.hashCode();
		result = 31 * result + canonicalTargetHostName.hashCode();
		return result;
	}

	public void serialize(final @NotNull AtomicWriter writer)
	{
		writer.writeUnsigned16BitInteger(priority);
		writer.writeUnsigned16BitInteger(weight);
		writer.writeUnsigned16BitInteger(port);
		canonicalTargetHostName.serialize(writer);
	}
}
